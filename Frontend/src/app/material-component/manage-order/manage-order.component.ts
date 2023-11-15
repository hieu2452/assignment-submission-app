import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { error } from 'console';
import { Product } from 'src/app/model/product.model';
import { BillService } from 'src/app/services/bill.service';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';


@Component({
  selector: 'app-manage-order',
  templateUrl: './manage-order.component.html',
  styleUrls: ['./manage-order.component.scss']
})
export class ManageOrderComponent implements OnInit {
  displayedColumns: string[] = ['name', 'category', 'price', 'quantity', 'total', 'edit'];
  dataSource: any = [];
  manageOrderForm: any = FormGroup;
  categorys: any = [];
  products: any = [];
  price: any;
  totalAmount: number = 0;
  responseMessage: any;
  addedProducts: Product[] = [];

  constructor(
    private formBulider: FormBuilder,
    private categoryService: CategoryService,
    private productService: ProductService,
    private billService: BillService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router) { }

  ngOnInit(): void {
    this.getProducts();
    this.manageOrderForm = this.formBulider.group({
      name: [null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      email: [null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]],
      contactNumber: [null, [Validators.required]],
      paymentMethod: [null, [Validators.required]],
      products: [null, [Validators.required]],
    });
  }

  getProducts() {
    this.productService.getProducts().subscribe((response: Product[]) => {
      this.products = response

    }, (error: any) => {
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  addToCart(product: Product) {
    if (this.addedProducts.includes(product)) return;
    product.quantity = 1
    this.addedProducts.push(product);
    this.manageOrderForm.controls['products'].setValue(this.addedProducts);
    let total_amount = 0;
    for (let product of this.addedProducts) {
      total_amount += product.quantity * product.price;
    }
    this.totalAmount = total_amount;
    this.dataSource = new MatTableDataSource<Product>(this.addedProducts);
  }

  changeValue(element: Product) {
    this.manageOrderForm.controls['products'].setValue(this.addedProducts);
    let total_amount = 0;
    for (let product of this.addedProducts) {
      total_amount += product.quantity * product.price;
    }
    this.totalAmount = total_amount;
  }

  validateSubmit() {
    var formData = this.manageOrderForm.value;
    if (formData.addedProducts === null || this.manageOrderForm.controls['email'].value === null ||
      formData.contactNumber === null || formData.paymentMethod === null) {
      return true;
    } else {
      return false;
    }
  }

  handleDeleteAction(element: any) {
    this.addedProducts = this.addedProducts.filter(product => product.id !== element.id)
    this.totalAmount -= element.price * element.quantity;
    this.dataSource = new MatTableDataSource(this.addedProducts);
  }

  downloadFile(fileName: string) {
    // var data = {
    //   uuid : fileName
    // }
    // this.billService.getPdf(data).subscribe((resonse:any)=>{
    //     saveAs(resonse , fileName +".pdf");
    // })
  }

  applyFilter(event: any) {
    const filterValue = (event.target as HTMLInputElement).value;
    console.log(filterValue)
    this.products = this.products.filter((product: Product) => {
      return product.name.toLowerCase().includes(filterValue.toLowerCase());
    })

    if(filterValue === '') {
      this.getProducts();
    }
  }

  handleSubmit() {
    const formData = this.manageOrderForm.value;
    const data = {
      name: formData.name,
      email: formData.email,
      contactNumber: formData.contactNumber,
      paymentMethod: formData.paymentMethod,
      products: formData.products.map(({ category, ...rest }: any) => rest)
    }
    this.billService.addBill(data).subscribe((response: any) => {
      this.snackbarService.openSnackBar("Create bill succesfully", 'success');
    }, (error: any) => {
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }
}
