import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { PanelModule } from 'primeng/panel';
import { SplitterModule } from 'primeng/splitter';
import { TabMenuModule } from 'primeng/tabmenu';
import { ClienteComponent } from "../../../cliente/component/cliente/cliente.component";

@Component({
  selector: 'app-menuapp',
  standalone: true,
  imports: [TabMenuModule, PanelModule, ClienteComponent],
  templateUrl: './menuapp.component.html',
  styleUrl: './menuapp.component.css'
})
export class MenuappComponent implements OnInit{

  items: MenuItem[] | undefined;

  ngOnInit(): void {
    this.items = [
      { label: 'Inicio', icon: 'pi pi-home', routerLink: "/home" },
      { label: 'Clientes', icon: 'pi pi-chart-line', routerLink: "/clientes-list" },
      { label: 'Productos', icon: 'pi pi-list', routerLink: "/productos-list" },
      { label: 'Gestionar pedidos', icon: 'pi pi-inbox', routerLink:"/pedido-gestion"}
    ]
  }

}
