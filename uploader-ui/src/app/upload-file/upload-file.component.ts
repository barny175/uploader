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

  constructor(private uploadService: UploadFileService) { }

  ngOnInit(): void {
  }

  selectFile(files) {
    this.fileToUpload = files.target.files.item(0);
  }

  onUpload() {
    this.uploadService.upload(this.fileToUpload, this.description).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
        //   this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
        //   this.message = event.body.message;
        }
      },
      err => {
        // this.progress = 0;
        // this.message = 'Could not upload the file!';
        this.fileToUpload = undefined;
      });
  }
}
