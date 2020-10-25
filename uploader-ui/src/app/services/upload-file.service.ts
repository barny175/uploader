import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {
  private baseUrl = 'http://localhost:18080';

  constructor(private http: HttpClient) { }

  upload(file: File, description: string): Observable<HttpEvent<any>> {

    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('description', description);

    const req = new HttpRequest('POST', `${this.baseUrl}/upload`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }
}
