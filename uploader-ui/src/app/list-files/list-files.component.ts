import { Component, OnInit,
  Input, OnChanges, SimpleChanges } from '@angular/core';
import { ListFilesService } from 'src/app/services/list-files.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-list-files',
  templateUrl: './list-files.component.html',
  styleUrls: ['./list-files.component.css']
})
export class ListFilesComponent implements OnInit, OnChanges {
  nameSearch: string;
  _descSearch: string;
  sizeSearch: number;

  get descSearch() { return this._descSearch; }
  set descSearch(descSearch: string) {
    this._descSearch = descSearch;
    this.loadFiles();
  }

  files;

  constructor(private filesService: ListFilesService) {
    this.loadFiles();
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    for (const propName in changes) {
      console.log(`${propName}`);
      if (propName === 'nameSearch') {
        const chng = changes[propName];
        const cur  = JSON.stringify(chng.currentValue);
        console.log(`${propName}: currentValue = ${cur}`);
      }
    }
  }

  remove(id) {
    this.filesService.remove(id)
      .subscribe(response => {
        this.loadFiles();
      }
    );
  }

  loadFiles() {
    console.log("Load files");
    this.filesService.list({ description: this._descSearch, name: this.nameSearch, size: this.sizeSearch }).subscribe(
      response => {
        this.files = response.files
      }
    );
  }

  onEnter(value: string) {
    console.log(`Filter names (${value})`);
    this.nameSearch = value;
    this.loadFiles();
  }

  onSizeEnter(value: number) {
    console.log(`Filter size (${value})`);
    this.sizeSearch = value;
    this.loadFiles();
  }
}
