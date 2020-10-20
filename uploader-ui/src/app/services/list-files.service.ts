import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Files } from './file';

@Injectable({
  providedIn: 'root'
})
export class ListFilesService {
  private baseUrl = 'http://localhost:18080';

  constructor(private http: HttpClient) { }

  list(): Observable<Files> {
    const req = {
      observe: 'body',
      reportProgress: true,
      responseType: 'json'
    };

    return this.http.get<Files>(`${this.baseUrl}/list`);
  }
}
