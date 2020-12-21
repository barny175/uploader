import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpEvent, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Files } from './file';

@Injectable({
  providedIn: 'root'
})
export class ListFilesService {
  private baseUrl = 'http://localhost:18080';

  constructor(private http: HttpClient) { }

  list(description): Observable<Files> {
    const req = {
      observe: 'body',
      reportProgress: true,
      responseType: 'json'
    };

    const params = description
      ? new HttpParams().set("description", description)
      : null;

    const options = {
      params: params
    };

    return this.http.get<Files>(`${this.baseUrl}/list`, options);
  }

  remove(id) {
    console.log("remove " + id);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.delete(`${this.baseUrl}/${id}`, httpOptions);
  }
}
