import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { error } from 'console';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ProductParam } from 'src/app/model/product-params';
import { Product } from 'src/app/model/product.model';
import { BillService } from 'src/app/services/bill.service';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { saveAs } from 'src/app/shared/save-file';


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
  productQueryParam: any = FormGroup;
  productParams = new ProductParam();

  constructor(
    private formBulider: FormBuilder,
    private categoryService: CategoryService,
    private productService: ProductService,
    private billService: BillService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router,
    private ngService: NgxUiLoaderService) { }

  ngOnInit(): void {
    this.getProducts(this.productParams);
    this.manageOrderForm = this.formBulider.group({
      name: [null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      email: [null, [Validators.pattern(GlobalConstants.emailRegex)]],
      contactNumber: [null, []],
      paymentMethod: [null, [Validators.required]],
      products: [null, [Validators.required]],
    });

    this.getCategorys()

    this.productQueryParam = this.formBulider.group({
      category: [null],
      price: [null],
    });
    this.productQueryParam.controls['category'].setValue('')
    this.productQueryParam.controls['price'].setValue('')
  }

  getCategorys() {
    this.categoryService.getCategorys().subscribe((response: any) => {
      this.categorys = response;
    }, (error) => {
      console.error(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  applyProductFilter(): void {
    let data = this.productQueryParam.value;
    this.productParams.category = data.category;
    this.productParams.price = data.price;
    this.getProducts(this.productParams)
  }

  getProducts(productParam: ProductParam) {
    this.productService.getProducts(productParam).subscribe((response: Product[]) => {
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
    if (formData.products === null || formData.paymentMethod === null) {
      return true;
    } else {
      return false;
    }
  }

  handleDeleteAction(element: any) {
    this.addedProducts = this.addedProducts.filter(product => product.id !== element.id)
    this.totalAmount -= element.price * element.quantity;
    this.dataSource = new MatTableDataSource(this.addedProducts);
    this.validateSubmit()
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
      let result = product.name.toLowerCase().includes(filterValue.toLowerCase()) || product.price.toString().includes(filterValue.toLowerCase());
      return result;
    })
    if (filterValue === '') {
      this.getProducts(this.productParams);
    }
  }

  handleSubmit() {
    this.ngService.start();
    const formData = this.manageOrderForm.value;
    const data = {
      name: formData.name,
      email: formData.email,
      contactNumber: formData.contactNumber,
      paymentMethod: formData.paymentMethod,
      products: formData.products
    }
    this.billService.addBill(data).subscribe((response: any) => {
      this.ngService.stop();
      this.billService.getPdf(response.id).subscribe((response: any) => {
        saveAs(response, 'invoice');
      }, (error) => {
        console.log(error)
      })
      this.snackbarService.openSnackBar("Create bill succesfully", 'success');
    }, (error: any) => {
      this.ngService.stop();
      console.log(error.error?.message);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
    this.totalAmount = 0;
    this.addedProducts = [];
    this.dataSource = new MatTableDataSource<Product>(this.addedProducts);
  }

}
