import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../model/page';
import { Cliente } from '../model/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private  url="http://localhost:8080/app/web/rest/clientes";

  constructor(private http: HttpClient) {

   }

   findAll(page: number, pageSize: number) : Observable<any> {
    return this.http.get<any>(`${this.url}/client-list?page=${page}&size=${pageSize}`);
  }

  save(cliente: Cliente) : Observable<any>{
    return this.http.post<any>(this.url, cliente, {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      })
    });
  }

  update(cliente: Cliente) : Observable<any>{
    return this.http.put<any>(this.url,cliente, {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      })
    })

  }
  delete(codigo: number) : Observable<void> {
    return this.http.delete<void>(`${this.url}?codCli=${codigo}`)
  }

  findByRucOrDocument(documento : string) : Observable<any> {
    return this.http.get<any>(`${this.url}/client-doc?nroDoc=${documento}`)
  }

  findByNombreOrRuc(filtro : string, page : number, size: number) : Observable<any> {
    return this.http.get<any>(`${this.url}/client-fil?cliNom=${filtro}&page=${page}&size=${size}`);
  }

}
