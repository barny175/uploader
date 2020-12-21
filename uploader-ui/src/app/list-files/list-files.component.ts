import { Component, OnInit,
  Input, OnChanges, SimpleChanges } from '@angular/core';
import { ListFilesService } from 'src/app/services/list-files.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-list-files-search',
  template: '<input type="text" name="nameSearch" [(ngModel)]="nameSearch" placeholder="search"/>',
  styleUrls: ['./list-files.component.css']
})
export class ListFilesSearchComponent implements OnChanges {
  @Input() nameSearch: string;

  ngOnChanges(changes: SimpleChanges): void {
    for (const propName in changes) {
      console.log(`${propName}`);
    }
  }
}

@Component({
  selector: 'app-list-files',
  templateUrl: './list-files.component.html',
  styleUrls: ['./list-files.component.css']
})
export class ListFilesComponent implements OnInit, OnChanges {
  nameSearch: string;
  _descSearch: string;
  get descSearch() { return this._descSearch; }
  set descSearch(descSearch: string) {
    this._descSearch = descSearch;
    this.searchDesc();
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

  searchDesc() {
    console.log("Search description " + this._descSearch);
    this.loadFiles();
  }

  loadFiles() {
    console.log("Load files");
    this.filesService.list(this._descSearch).subscribe(
      response => {
        this.files = response.files
      }
    );
  }
}
