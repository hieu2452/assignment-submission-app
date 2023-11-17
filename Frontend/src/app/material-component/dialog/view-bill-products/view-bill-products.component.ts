import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { BillService } from 'src/app/services/bill.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { MatTableDataSource } from '@angular/material/table';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { log } from 'console';

@Component({
  selector: 'app-view-bill-products',
  templateUrl: './view-bill-products.component.html',
  styleUrls: ['./view-bill-products.component.scss']
})
export class ViewBillProductsComponent implements OnInit {

  dataplayedColumns: string[] = ['name', 'price', 'quantity', 'total'];
  dataSource: any;
  data: any;
  totalAmount: number = 0;

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData: any,
    public dialogRef: MatDialogRef<ViewBillProductsComponent>) { }

  ngOnInit() {
    this.data = this.dialogData.data;
    this.dataSource = new MatTableDataSource(this.dialogData.data.products);
    console.log(this.data);
    let total_amount = 0;
    for (let product of this.data.products) {
      total_amount += product.quantity * product.price;
    }
    this.totalAmount = total_amount;
  }
}
