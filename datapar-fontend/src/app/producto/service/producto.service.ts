import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../model/page';
import { Producto } from '../../model/producto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private url = 'http://localhost:8080/app/web/rest/productos';


  constructor(private http: HttpClient) { }



  /*findAll(page: number, size: number): Observable<Page<Producto>> {
    return this.http.get<Page<Producto>>(`${this.url}/product-list?page=${page}&size=${size}`);
  }*/

  findAll(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.url}/product-list?page=${page}&size=${size}`);
  }

  findAllFilterProductoNombre(texto: string, page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.url}/produt-filt?nomProd=${texto}&page=${page}&size=${size}`);
  }

  save(product : Producto) : Observable<any> {
    return this.http.post<any>(this.url, product, {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      })
    });
  }

  update(product : Producto) : Observable<Page<any>> {
    return this.http.put<Page<any>>(this.url, product, {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      })
    });
  }

  delete(codigo : string): Observable<void> {
    return this.http.delete<void>(`${this.url}?codProd=${codigo}`);
  }

  findById(codigo : string): Observable<Producto> {
    return this.http.get<Producto>(`${this.url}/produt-id?idProd=${codigo}`);
  }

}
