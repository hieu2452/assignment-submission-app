import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ProductService } from 'src/app/services/product.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-manage-product',
  templateUrl: './manage-product.component.html',
  styleUrls: ['./manage-product.component.scss']
})
export class ManageProductComponent implements OnInit {
  // displayedColumns: string[] = ['name' , 'category' , 'description' , 'price' , 'quantity', 'image' , 'edit'];
  displayedColumns: string[] = ['name' , 'category' , 'description' , 'price' ,  'edit'];
  dataSource:any;
  length1:any;
  responseMessage:any;

    constructor(private productService:ProductService,
      private ngxService:NgxUiLoaderService,
    private dialog:MatDialog,
    private SnackbarService:SnackbarService,
    private router:Router) { }

  ngOnInit(): void {
    // this.ngxService.start();
    this.tableData();
  }

  tableData(){
    this.productService.getProductsForAdmin().subscribe((response:any)=>{
      this.dataSource = new MatTableDataSource(response);
    },(error:any)=>{
      console.log(error.error?.message);
      if(error.error?.message){
        this.responseMessage = error.error?.message;
      }else{
        this.responseMessage = GlobalConstants.genericError;
      }
      this.SnackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })

  }

  applyFilter(event:Event){
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleAddAction(){
    console.log(this.dataSource);
  }

  handleEditAction(value:any){

  }

  handleDeleteAction(value:any){

  }

  onChange(status:any,id:any){

  }
}
