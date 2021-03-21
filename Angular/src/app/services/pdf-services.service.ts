import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { observable, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PdfServicesService {

  constructor(private http: HttpClient) { }

  upload(form: { image: File, pdf: File, xCoordinate: 0, yCoordinate: 0, page: 0 }): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('image', form.image);
    formData.append('pdf', form.pdf);
    formData.append('xCoordinate', '' + form.xCoordinate);
    formData.append('yCoordinate', '' + form.yCoordinate); 
    formData.append('page', '' + form.page);


    const req = new HttpRequest('POST', `${environment.backEndUrl}/pdf/image`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }
}
