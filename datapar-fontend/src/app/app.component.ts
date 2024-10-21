import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuappComponent } from "./menu/component/menuapp/menuapp.component";
import { ClienteListComponent } from "./cliente/component/cliente-list/cliente-list.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteComponent } from "./cliente/component/cliente/cliente.component";
import { ProductoComponent } from "./producto/component/producto/producto.component";
import { PedidoComponent } from "./pedido/component/pedido/pedido.component";
import { ClientebusqComponent } from "./varios/dialogbusq/cliente/componente/clientebusq/clientebusq.component";
import { ProductobusqComponent } from "./varios/dialogbusq/producto/componente/productobusq/productobusq.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet,
    MenuappComponent, ClienteListComponent, ClienteComponent,
    ProductoComponent, PedidoComponent, ClientebusqComponent, ProductobusqComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'appweb1';
}
