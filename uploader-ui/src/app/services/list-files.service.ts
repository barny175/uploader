import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpEvent, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Files } from './file';
import { APP_CONFIG, AppConfig } from '../app.config';

@Injectable({
  providedIn: 'root'
})
export class ListFilesService {
  constructor(private http: HttpClient,
    @Inject(APP_CONFIG) private config: AppConfig) { }

  list(filter): Observable<Files> {
    const req = {
      observe: 'body',
      reportProgress: true,
      responseType: 'json'
    };

    const params = new HttpParams({ fromObject: filter });

    const options = {
      params: params
    };

    return this.http.get<Files>(`${this.config.baseUrl}/list`, options);
  }

  remove(id) {
    console.log("remove " + id);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.delete(`${this.config.baseUrl}/${id}`, httpOptions);
  }
}
