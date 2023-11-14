import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { BillService } from 'src/app/services/bill.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { MatTableDataSource } from '@angular/material/table';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-view-bill-products',
  templateUrl: './view-bill-products.component.html',
  styleUrls: ['./view-bill-products.component.scss']
})
export class ViewBillProductsComponent implements OnInit {

  constructor() { }

    ngOnInit(): void {
    
    }
    
}
