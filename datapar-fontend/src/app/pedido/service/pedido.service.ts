import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pedido } from '../../model/pedido';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  private url = "http://localhost:8080/app/web/rest/pedidos";

  constructor(private http: HttpClient) { }


  save(pedido : Pedido) : Observable<any> {
    return this.http.post<any>(this.url, pedido.convertEntityDto(), {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      })
    });
  }
  controlNumeroPedido(): Observable<any> {
    return this.http.get<string>(`${this.url}/controlNumPed`);
  }

  obtenerFechaServidor(): Observable<any> {
    return this.http.get<string>(`${this.url}/fechaServidor`);
  }

}
