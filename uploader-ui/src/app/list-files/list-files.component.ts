import { Component, OnInit } from '@angular/core';
import { ListFilesService } from 'src/app/services/list-files.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-list-files',
  templateUrl: './list-files.component.html',
  styleUrls: ['./list-files.component.css']
})
export class ListFilesComponent implements OnInit {
  files;
  constructor(private filesService: ListFilesService) {
    filesService.list().subscribe(
      response => {
        this.files = response.files
      }
    );
  }

  ngOnInit(): void {
  }

  remove(path) {
    this.filesService.remove(path);
  }

}
