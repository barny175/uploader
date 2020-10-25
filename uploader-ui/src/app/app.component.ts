import { Component } from '@angular/core';
import { UploadFileComponent } from './upload-file/upload-file.component';
import { ListFilesComponent } from './list-files/list-files.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Image Uploader';

  onNotify() {
      console.log("file uploaded");
  }
}
