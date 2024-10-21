import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { Cliente } from '../../../../../cliente/model/cliente';
import { ClienteService } from '../../../../../cliente/service/cliente.service';
import { ButtonModule } from 'primeng/button';
import { DynamicDialogComponent, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Ciudad } from '../../../../../model/ciudad';

@Component({
  selector: 'app-clientebusq',
  standalone: true,
  imports: [FormsModule, CommonModule,InputIconModule,
    IconFieldModule,InputTextModule, TableModule,ButtonModule],
  templateUrl: './clientebusq.component.html',
  styleUrl: './clientebusq.component.css'
})
export class ClientebusqComponent implements OnInit{

  clientes !: Cliente [];
  clienteSeleccionado : Cliente= {
    codigo: 0,
    nombre: "",
    documentoNro: "",
    ciudad: new Ciudad(),
    direccion:"",
    telefono: "",
    email: ""
  };
  textoFiltro !: string;
  page : number=0;
  size : number = 20;

  constructor(private service : ClienteService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig){

  }
  ngOnInit(): void {
    this.clienteSeleccionado= this.config.data.cliente;
  }


  selectCliente(_cli: Cliente): void {
    if(_cli){
      this.clienteSeleccionado= _cli;
      this.ref.close(this.clienteSeleccionado);
    }
  }

  filtrarCliente() : void {
    if(this.textoFiltro.trim() !== ""){
      this.service.findByNombreOrRuc(this.textoFiltro, this.page, this.size)
      .subscribe({
        next: (response) => {
          this.clientes = response.content;
        }
      });
    }
  }

}
