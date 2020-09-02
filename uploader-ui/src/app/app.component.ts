import { Component } from '@angular/core';
import { UploadFileService } from 'src/app/services/upload-file.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Image Uploader';

  fileToUpload: File = null;

  constructor(private uploadService: UploadFileService) { }

  processFile(files) {
    this.fileToUpload = files.files.item(0);
  }

  onUpload() {
    this.uploadService.upload(this.fileToUpload).subscribe(
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
