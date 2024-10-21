import { Routes } from '@angular/router';
import { ClienteListComponent } from './cliente/component/cliente-list/cliente-list.component';
import { ProductoListComponent } from './producto/component/producto-list/producto-list.component';
import { InicioHome1Component } from './menu/component/inicio-home1/inicio-home1.component';
import { PedidoComponent } from './pedido/component/pedido/pedido.component';

export const routes: Routes = [
  {path: "clientes-list", component: ClienteListComponent},
  {path: "productos-list", component: ProductoListComponent},
  //{path: "", redirectTo:"/home", pathMatch: "full"},
  {path: "home", component: InicioHome1Component},
  {path: "pedido-gestion", component: PedidoComponent},
];
