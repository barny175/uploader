import { Component, OnInit } from '@angular/core';
import { UploadFileService } from 'src/app/services/upload-file.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {

  fileToUpload: File = null;
  description: string = "Description";
  message = '';

  constructor(private uploadService: UploadFileService) { }

  ngOnInit(): void {
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
