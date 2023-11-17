
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { BillService } from 'src/app/services/bill.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { MatTableDataSource } from '@angular/material/table';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ViewBillProductsComponent } from '../dialog/view-bill-products/view-bill-products.component';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { error } from 'console';
import { saveAs } from 'src/app/shared/save-file';
import { NgxUiLoaderService } from 'ngx-ui-loader';


@Component({
  selector: 'app-view-bill',
  templateUrl: './view-bill.component.html',
  styleUrls: ['./view-bill.component.scss']
})
export class ViewBillComponent implements OnInit {

  displayedColumns: string[] = ['name', 'email', 'contactNumber', 'paymentMethod', 'createdDate', 'view'];
  dataSource: any;
  responseMessage: any;

  constructor(private billservice: BillService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router,
    private ngService: NgxUiLoaderService) { }

  ngOnInit(): void {
    this.tableData();
  }
  tableData() {
    this.billservice.getBills().subscribe((response: any) => {
      this.dataSource = new MatTableDataSource(response);
    }, (error: any) => {
      console.log(error)
      if (error.status === 404) {
        this.dataSource = new MatTableDataSource([]);
      }
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleViewAction(values: any) {

    const dialogConfog = new MatDialogConfig();
    dialogConfog.data = {
      data: values
    };
    dialogConfog.width = "100%";
    const dialogRef = this.dialog.open(ViewBillProductsComponent, dialogConfog);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

  }
  handleDeleteAction(values: any) {
    const dialogConfog = new MatDialogConfig;
    dialogConfog.data = {
      message: 'delete ' + values.name + ' bill',
      confirmation: true
    };
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfog);
    const sub = dialogRef.componentInstance.onEmistStatusChange.subscribe((response) => {
      this.deleteBill(values.id);
      dialogRef.close();
    })
  }
  deleteBill(id: any) {
    this.billservice.delete(id).subscribe((response: any) => {
      this.tableData();
      this.snackbarService.openSnackBar("Delete bill successfully", 'success');
    }, (error: any) => {

    })
  }

  downloadReportAction(values: any) {
    this.billservice.getPdf(values.id).subscribe((response: any) => {
      saveAs(response, 'invoice');
    }, (error) => {
      console.log(error)
    })
  }

  downloadFile(fileName: string, data: any) {

    this.billservice.getPdf(data).subscribe((response: any) => {
      // saveAs(response, fileName + '.pdf');
    })
  }

}
