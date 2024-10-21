import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { Cliente } from '../../model/cliente';
import { Ciudad } from '../../../model/ciudad';
import { CiudadService } from '../../../ciudad/service/ciudad.service';
import { Page } from '../../../model/page';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { ClienteService } from '../../service/cliente.service';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-cliente',
  standalone: true,
  imports: [FormsModule,
    CommonModule,
    InputTextModule,CardModule, ButtonModule,
    DialogModule,CardModule,ConfirmDialogModule,ToastModule],
  providers: [ConfirmationService, MessageService],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.css'
})
export class ClienteComponent implements OnInit{

  visible : boolean = true;

  cliente : Cliente = {
    codigo: 0,
    nombre : '',
    documentoNro : '',
    ciudad : new Ciudad(),
    direccion : '',
    telefono : '',
    email : ''
  }

  ciudades : Ciudad[]= [];
  page !: Page<Ciudad>;
  isDelete : boolean = false;
  isNew : boolean = true;


  constructor(private serviceCiudad: CiudadService,
    private serviceCliente: ClienteService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {

  }


  ngOnInit(): void {
    this.cliente= this.config.data.cliente;
    this.isDelete= this.config.data.isDelete;
    this.isNew= this.config.data.isNew
    this.findAllCiudad();
  }

  findAllCiudad() {
    this.serviceCiudad.findAll(0,20).subscribe({
      next: (response) => {
        this.page= response;
        this.ciudades= response.content;
      }
    });
  }

  saveCliente(event : Event):void {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Desea grabar los datos del cliente?',
      header: 'ATENCIÓN',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon:"none",
      rejectIcon:"none",
      rejectButtonStyleClass:"p-button-text",
      accept: () => {
        if(this.isNew){
          this.serviceCliente.save(this.cliente).subscribe(
            (clienteSave : any)=> {
              this.messageService.add({ severity: 'info', summary: 'Operación completada', detail: 'Se ha agregado un nuevo cliente', life: 3000 });
              this.closeDialogo(clienteSave) ;
            },
            (error: any)=> {
              const _errorMensaje = error.error;
              this.messageService.add({ severity: 'error', summary: 'Atención', detail: _errorMensaje, life: 3000 });
            }
          )
        } else{
          this.serviceCliente.update(this.cliente).subscribe(
            (clienteSave : any)=> {
              this.messageService.add({ severity: 'info', summary: 'Operación completada', detail: 'Se ha actualizado los datos del cliente', life: 3000 });
              this.closeDialogo(clienteSave) ;
            },
            (error: any)=> {
              const _errorMensaje = error.error;
              this.messageService.add({ severity: 'error', summary: 'Atención', detail: _errorMensaje, life: 3000 });
            }
          )
        }
      },
      reject: () => {
          this.messageService.add({ severity: 'error', summary: 'Operación cancelada', detail: 'You have rejected', life: 3000 });
      }
  });
  }
  reset() {
    this.cliente={
      codigo: 0,
      nombre : '',
      documentoNro : '',
      ciudad : new Ciudad(),
      direccion : '',
      telefono : '',
      email : ''
    }
  }

  deleteCliente(_cliente : Cliente, event: Event) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Desea eliminar el registro del cliente?',
      header: 'ATENCIÓN',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass:"p-button-danger p-button-text",
      rejectButtonStyleClass:"p-button-text p-button-text",
      acceptIcon:"none",
      rejectIcon:"none",

      accept: () => {
          this.serviceCliente.delete(_cliente.codigo).subscribe(
            ()=> {
              this.messageService.add({ severity: 'info', summary: 'Operación completada', detail: 'Se ha eliminado el registro del cliente', life: 3000 });
              this.closeDialogo(_cliente) ;
            },
            (error: any)=> {
              const _errorMensaje = error.error;
              this.messageService.add({ severity: 'error', summary: 'Atención', detail: _errorMensaje, life: 3000 });
            }
          );
      },
      reject: () => {
          this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected' });
      }
  });
  }


  compareCity(c1: Ciudad, c2: Ciudad) : boolean {
    return c1 && c2 ? c1.codigo === c2.codigo : c1===c2;
  }

  closeDialogo(_data: any){
    setTimeout(()=>{
      this.ref.close(_data);
    },1500)
  }
  closeDialogoImediate(_data: any){
    this.ref.close(_data);
  }

}
