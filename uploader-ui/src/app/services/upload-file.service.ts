import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { APP_CONFIG, AppConfig } from '../app.config';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private http: HttpClient,
    @Inject(APP_CONFIG) private config: AppConfig) { }

  upload(file: File, description: string): Observable<HttpEvent<any>> {

    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('description', description);

    const req = new HttpRequest('POST', `${this.config.baseUrl}/upload`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }
}
