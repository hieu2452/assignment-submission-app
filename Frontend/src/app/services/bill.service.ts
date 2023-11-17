import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BillService {

  url = environment.apiUrl;
  constructor(private httpClient: HttpClient) { }

  generateReport(data: any) {
    return this.httpClient.post(this.url + "/bill/generateReport", data, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }

  addBill(data: any) {
    return this.httpClient.post(this.url + "/bill/add", data, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }

  getPdf(id: any): Observable<Blob> {
    return this.httpClient.get(this.url + "/bill/get/" + id, { responseType: 'blob' });
  }

  getBills() {
    return this.httpClient.get(this.url + "/bill/get");
  }
  delete(id: any) {
    return this.httpClient.delete(this.url + "/bill/delete/" + id, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    });
  }
}
