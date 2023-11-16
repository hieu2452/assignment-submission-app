import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ProductService } from 'src/app/services/product.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ProductComponent } from '../dialog/product/product.component';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';

@Component({
  selector: 'app-manage-product',
  templateUrl: './manage-product.component.html',
  styleUrls: ['./manage-product.component.scss']
})
export class ManageProductComponent implements OnInit {
  // displayedColumns: string[] = ['name' , 'category' , 'description' , 'price' , 'quantity', 'image' , 'edit'];
  displayedColumns: string[] = ['name', 'category', 'description', 'price', 'edit'];
  dataSource: any;
  length1: any;
  responseMessage: any;

  constructor(private productService: ProductService,
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private SnackbarService: SnackbarService,
    private router: Router) { }

  ngOnInit(): void {
    // this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.productService.getProductsForAdmin().subscribe((response: any) => {
      this.dataSource = new MatTableDataSource(response);
    }, (error: any) => {
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.SnackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleAddAction() {
    const dialogConfog = new MatDialogConfig();
    dialogConfog.data = {
      action: 'Add'
    };
    dialogConfog.width = "850px";
    const dialogRef = this.dialog.open(ProductComponent, dialogConfog);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onAddProduct.subscribe((response) => {
      this.tableData();
    })
  }

  handleEditAction(values: any) {
    console.log(values)
    const dialogConfog = new MatDialogConfig();
    dialogConfog.data = {
      action: 'Edit',
      data: values
    };
    const dialogRef = this.dialog.open(ProductComponent, dialogConfog);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onEditProduct.subscribe((response) => {
      this.tableData();
    })
  }
  handleDeleteAction(values: any) {
    const dialogConfog = new MatDialogConfig();
    dialogConfog.data = {
      message: 'delete ' + values.name + ' product ',
      confirmation: true
    };
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfog);
    const sub = dialogRef.componentInstance.onEmistStatusChange.subscribe((response) => {
      this.deleteProduct(values.id);
      dialogRef.close();
    })
  }

  deleteProduct(id: any) {
    this.productService.delete(id).subscribe((response: any) => {
      this.tableData();
      this.responseMessage = response?.message;
      this.SnackbarService.openSnackBar(this.responseMessage, "success");
    }, (error: any) => {
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.SnackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  onChange(status: any, id: any) {
    var data = {
      status: status.toString(),
      id: id
    }
    this.productService.updateStatus(id).subscribe((response: any) => {
      this.responseMessage = response?.message;
      this.SnackbarService.openSnackBar("update priduct status successfully", "success");
    }, (error: any) => {
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.SnackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }
}
