import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';
import { ClienteService } from '../../service/cliente.service';
import { Cliente } from '../../model/cliente';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Page } from '../../../model/page';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DialogService, DynamicDialogModule, DynamicDialogRef } from 'primeng/dynamicdialog';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { ClienteComponent } from '../cliente/cliente.component';
import { Footer } from '../../../varios/foosterButton';
import { literal } from '@angular/compiler';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'app-cliente-list',
  standalone: true,
  imports: [CommonModule, FormsModule,
    TableModule, ButtonModule, CardModule, DynamicDialogModule,
    ToastModule,ConfirmDialogModule,PaginatorModule],
  providers: [DialogService, MessageService],
  templateUrl: './cliente-list.component.html',
  styleUrl: './cliente-list.component.css'
})
export class ClienteListComponent implements OnInit {

  clientes !: Cliente[];
  pages !: Page<Cliente>;
  ref: DynamicDialogRef | undefined;
  totalItems: number = 0;
  page: number = 0;
  size: number = 10;
  totalPages: number = 0;

  constructor(private service: ClienteService,
    public dialogService: DialogService, public messageService: MessageService) {

  }
  ngOnInit(): void {
    this.findAllClientes();
  }
  findAllClientes() {
    this.service.findAll(this.page, this.size)
      .subscribe({
        next: (_pageResponse) => {
          this.pages = _pageResponse;
          this.clientes = _pageResponse.content;
          this.totalItems = _pageResponse.page.totalElements;
          this.totalPages = _pageResponse.page.totalPages;
          this.size = _pageResponse.page.size;
          this.page = _pageResponse.page.number;
        }
      });
  }

  cambiarPagina(event: any): void {
    this.page = event.page;
    this.findAllClientes();
  }

  addCliente(): void {
    this.openDialogo(new Cliente(), true, false);
  }
  editCliente(cliente: Cliente): void {
    this.openDialogo(cliente, false, false);
  }
  deleteCliente(cliente: Cliente) : void{
    this.openDialogo(cliente, false, true);
  }

  openDialogo(cliente: Cliente, _new : boolean, _delete: boolean): void {
    this.ref = this.dialogService.open(ClienteComponent, {
      data: {cliente: {...cliente},
        isNew: _new,
        isDelete: _delete
      },
      header: 'Cliente',
      footer: "",
      width: '50vw',
      contentStyle: { overflow: 'auto' },
      breakpoints: {
        '960px': '75vw',
        '640px': '90vw'
      }
      /*templates: {
        footer: Footer
      }*/
    })
    this.ref.onClose.subscribe((data: any) => {
      if (data) {
        //this.messageService.add({ severity: 'info', summary: 'Operación completada', detail: 'Se ha agregado un nuevo cliente', life: 3000 });
        this.findAllClientes();
      } else {
      }
    });
  }

}
