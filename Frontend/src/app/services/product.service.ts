import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Product } from '../model/product.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  url = environment.apiUrl;
  constructor(private httpClient: HttpClient) { }

  add(data: any) {
    return this.httpClient.post(this.url + "/product/add", data)
  }

  update(data: any) {
    return this.httpClient.post(this.url + "/product/update", data)
  }

  getProductsForAdmin() {
    return this.httpClient.get<Product[]>(`${this.url}/product/admin/get`);
  }

  getProductsById(id: any) {
    return this.httpClient.get(`${this.url}/product/get/${id}`);
  }

  getProductsByCategory(id: any) {
    return this.httpClient.get(`${this.url}/product/getByCategory/${id}`);
  }

  // updateStatus(data:any){
  //   return this.httpClient.post(this.url +"/product/updateProductStatus" , data,{
  //     headers: new HttpHeaders().set('Content-Type' , "application/json")
  //   })
  // }

  // delete(id:any){
  //   return this.httpClient.post(this.url +"/product/enable/"+ id ,{
  //     headers: new HttpHeaders().set('Content-Type' , "application/json")
  //   })
  // }

  // getProductByCategory(id:any){
  //   return this.httpClient.get(this.url + "/product/getByCategory/"+id);
  // }

  // getById(id:any){
  //   return this.httpClient.get(this.url + "/product/getProductById/"+id);
  // }
}
