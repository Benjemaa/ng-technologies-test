import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { PdfServicesService } from '../services/pdf-services.service';

@Component({
  selector: 'app-pdf-modifier',
  templateUrl: './pdf-modifier.component.html',
  styleUrls: ['./pdf-modifier.component.css']
})
export class PdfModifierComponent implements OnInit {

  image: File;
  pdf: File;
  xCoordinate: 0;
  yCoordinate: 0;
  page: 0;
  progress = 0;
  message = '';
  enabled = false;
  loading=false;


  constructor(private uploadService: PdfServicesService) { }

  ngOnInit() {
  }

  selectPdf(event) {
    this.pdf = event.target.files[0];
  }
  selectImage(event) {
    this.image = event.target.files[0];
  }

  upload() {
    this.progress = 0;
    this.loading=true;
    let currentForm = {
      image: this.image,
      pdf: this.pdf,
      xCoordinate: this.xCoordinate,
      yCoordinate: this.yCoordinate,
      page: this.page
    }
    this.uploadService.upload(currentForm).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.message = event.body.message;
          this.openBase64NewTab(event.body.pdf)
        }
        console.log(event)
      },
      err => {
        this.progress = 0;
        this.message = 'Could not upload the file!';
      });
      
    this.loading=false;
  }

  // the next two function just gor them to open the pdf in new tab (they are not mine )
  openBase64NewTab(base64Pdf: string): void {
    var blob = this.base64toBlob(base64Pdf);
    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
      window.navigator.msSaveOrOpenBlob(blob, "pdfBase64.pdf");
    } else {
      const blobUrl = URL.createObjectURL(blob);
      window.open(blobUrl);
    }
  }
  base64toBlob(base64Data: string) {
    const sliceSize = 1024;
    const byteCharacters = atob(base64Data);
    const bytesLength = byteCharacters.length;
    const slicesCount = Math.ceil(bytesLength / sliceSize);
    const byteArrays = new Array(slicesCount);
  
    for (let sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
      const begin = sliceIndex * sliceSize;
      const end = Math.min(begin + sliceSize, bytesLength);
  
      const bytes = new Array(end - begin);
      for (let offset = begin, i = 0; offset < end; ++i, ++offset) {
        bytes[i] = byteCharacters[offset].charCodeAt(0);
      }
      byteArrays[sliceIndex] = new Uint8Array(bytes);
    }
    return new Blob(byteArrays, { type: "application/pdf" });
  }
}
