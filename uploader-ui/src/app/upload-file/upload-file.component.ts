import { Component, OnInit } from '@angular/core';
import { UploadFileService } from 'src/app/services/upload-file.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Output, EventEmitter } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {

  fileToUpload: File = null;
  description: string = "Description";
  message = '';
  uploadForm;
  @Output() notify = new EventEmitter();

  constructor(
    private uploadService: UploadFileService,
    private formBuilder: FormBuilder,
  ) {
    this.uploadForm = this.formBuilder.group({
      file: '',
      description: ''
    })
  }

  ngOnInit(): void {
  }

  onSubmit(data) {
    console.log("Form submitted.");
    this.uploadForm.reset();
  }

  selectFile(files) {
    this.fileToUpload = files.target.files.item(0);
    this.resetError();
  }

  onUpload() {
    if (!this.description) {
      this.message = 'Failed';
      this.description = 'Required';
      return;
    }

    this.uploadService.upload(this.fileToUpload, this.description).subscribe(
      event => {
        if (event instanceof HttpResponse) {
           this.message = 'Success';
           this.notify.emit();
           console.log("Successfully uploaded.")
        }
      },
      err => {
        this.message = 'Could not upload the file!';
        this.fileToUpload = undefined;
      });
  }

  resetError() {
    this.message = '';
  }
}
