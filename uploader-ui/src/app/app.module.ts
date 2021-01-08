import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from "@angular/common";

import { AppComponent } from './app.component';
import { UploadFileComponent } from './upload-file/upload-file.component';
import { ListFilesComponent } from './list-files/list-files.component';

import { AppConfigModule } from './app.config';

import { NumberDirective } from './numbers-only.directive';

@NgModule({
  declarations: [
    AppComponent,
    UploadFileComponent,
    ListFilesComponent,
    NumberDirective,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    AppConfigModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
