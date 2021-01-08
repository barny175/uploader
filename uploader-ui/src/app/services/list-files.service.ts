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

    const options = {
      params: this.httpParams(filter)
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

  httpParams(filter: object): HttpParams {
      let params = new HttpParams();
      Object.keys(filter).forEach(key => {
        filter[key] && (params = params.append(key, filter[key]))
      });

      return params;
  }
}
