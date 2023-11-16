import { InjectFlags } from '@angular/compiler/src/core';
import { Component, EventEmitter, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { CategoryService } from 'src/app/services/category.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  onAddCategory = new EventEmitter();
  onEditCategory = new EventEmitter();
  categoryForm: any = FormGroup;
  dialogAction: any = "Add";
  action: any = "Add";

  responseMessage: any;
  constructor(
    private formBulider: FormBuilder,
    protected categoryService: CategoryService,
    public dialogRef: MatDialogRef<CategoryComponent>,
    private snackbarService: SnackbarService,
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private router: Router,
    private ngxSrevice: NgxUiLoaderService
  ) { }

  ngOnInit(): void {
    this.categoryForm = this.formBulider.group({
      name: [null, [Validators.required]]
    });
    if (this.dialogData.action === 'Edit') {
      this.dialogAction = "Edit";
      this.action = "Update";
      this.categoryForm.patchValue(this.dialogData.data);
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
    this.ngxSrevice.start();
    var formData = this.categoryForm.value;
    var data = {
      name: formData.name
    }
    this.categoryService.add(data).subscribe((response: any) => {
      this.ngxSrevice.stop();
      this.dialogRef.close();
      this.onAddCategory.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar("Add new category successfully", "success");
    }, (error) => {
      this.dialogRef.close();
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
    this.ngxSrevice.start();
    var formData = this.categoryForm.value;
    var data = {
      id: this.dialogData.data.id,
      name: formData.name
    }
    this.categoryService.update(data).subscribe((response: any) => {
      this.ngxSrevice.stop();
      this.dialogRef.close();
      this.onEditCategory.emit();
      this.responseMessage = response.message;
      alert("Update Category Successfully");
      this.snackbarService.openSnackBar(this.responseMessage, "success");
    }, (error) => {
      this.ngxSrevice.stop();
      this.dialogRef.close();
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
