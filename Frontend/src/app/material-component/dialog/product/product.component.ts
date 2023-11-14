import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  onAddProduct = new EventEmitter();
  onEditProduct = new EventEmitter();
  productForm: any = FormGroup;
  dialogAction: any = "Add";
  action: any = "Add";
  responseMessage: any;
  categorys: any = [];
  display: FormControl = new FormControl("", Validators.required);
  file_store: FileList | undefined;
  file_list: Array<string> = [];
  selectedFile: any = undefined;
  data = {};


  constructor(@Inject(MAT_DIALOG_DATA) public dialogData: any,
    private formBulider: FormBuilder,
    protected productService: ProductService,
    public dialogRef: MatDialogRef<ProductComponent>,
    private snackbarService: SnackbarService,
    private categoryService: CategoryService,
  ) { }

  ngOnInit(): void {
    this.productForm = this.formBulider.group({
      id: [null],
      name: [null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      category: [null, Validators.required],
      price: [null, Validators.required],
      description: [null, Validators.required],
    });
    if (this.dialogData.action === 'Edit') {
      this.dialogAction = "Edit";
      this.action = "Update";
      this.productForm.patchValue(this.dialogData.data);
      this.productForm.controls.category.setValue(this.dialogData.data.category.name)
    }
    this.getCategorys();
  }

  handleFileInputChange(l: FileList): void {
    this.file_store = l;
    if (l.length) {
      const f = l[0];
      const count = l.length > 1 ? `(+${l.length - 1} files)` : "";
      this.display.patchValue(`${f.name}${count}`);
    } else {
      this.display.patchValue("");
    }
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

  onFileSelected(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.selectedFile = file;
    }
  }


  handleSubmit() {
    if (this.dialogAction === "Edit") {
      this.edit();
    } else {
      this.add();
    }
  }
  add() {
    const form_Data: FormData = new FormData();
    var formData = this.productForm.value;
    var data = {
      name: formData.name,
      description: formData.description,
      price: formData.price,
      category: formData.category,
    }
    if (this.file_store) {
      form_Data.append('file', this.file_store[0]);
    }

    form_Data.append('model', JSON
      .stringify(data));


    this.productService.add(form_Data).subscribe((response: any) => {
      this.dialogRef.close();
      this.onAddProduct.emit();
      this.snackbarService.openSnackBar("Successfully Add Product", "success");
    }, (error) => {
      this.dialogRef.close();
      console.error(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      alert(this.responseMessage + " " + GlobalConstants.error);
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  edit() {
    const form_Data: FormData = new FormData();

    var formData = this.productForm.value;
    var data = {
      id: this.dialogData.data.id,
      name: formData.name,
      category: formData.category,
      price: formData.price,
      description: formData.description,
    }
    this.data = data;
    if (this.file_store) {
      form_Data.append('file', this.file_store[0]);
    }

    form_Data.append('model', JSON
      .stringify(data));

    this.productService.update(form_Data).subscribe((response: any) => {
      this.dialogRef.close();
      this.onEditProduct.emit();
      this.snackbarService.openSnackBar("Successfully Update Product", "success");
    }, (error) => {
      this.dialogRef.close({ event: this.action, data: this.data });
      console.error(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      alert(this.responseMessage + " " + GlobalConstants.error);
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

}
