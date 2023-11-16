import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Product } from '../model/product.model';
import { Observable } from 'rxjs';
import { ProductParam } from '../model/product-params';
import { map } from 'rxjs/operators';

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

  getProducts(productParam: ProductParam) {
    let params = new HttpParams();
    params = params.append('category', productParam.category);
    params = params.append('price', productParam.price);
    return this.httpClient.get<any>(`${this.url}/product/get`, { observe: 'response', params }).pipe(
      map(response => {
        if (response.body)
          return response.body
      })
    );
  }

  getProductsById(id: any) {
    return this.httpClient.get(`${this.url}/product/get/${id}`);
  }

  getProductsByCategory(id: any) {
    return this.httpClient.get(`${this.url}/product/getByCategory/${id}`);
  }

  updateStatus(id: number) {
    return this.httpClient.put(`${this.url}/product/update-status/${id}`, {}, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }


  delete(id: any) {
    return this.httpClient.delete(`${this.url}/product/delete/${id}`, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }

  // getProductByCategory(id:any){
  //   return this.httpClient.get(this.url + "/product/getByCategory/"+id);
  // }

  getById(id: any) {
    return this.httpClient.get(this.url + "/product/getProductById/" + id);
  }
}
