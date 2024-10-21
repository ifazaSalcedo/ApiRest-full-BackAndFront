import { Component } from '@angular/core';
import { DynamicDialogComponent, DynamicDialogConfig, DynamicDialogModule, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ButtonModule } from 'primeng/button';

@Component({
    selector: 'footer',
    standalone: true,
    imports: [ButtonModule],
    template:  `<div class="flex w-full justify-content-end mt-3">
            <p-button type="button"  label="Agregar" severity="primary" [disabled]="this.conf.data.detalle.subtotal===0" (onClick)="pasarProducto()" />
            <p-button type="button" label="Cancel"  severity="secondary" (onClick)="closeDialog({ buttonType: 'Cancel', summary: 'Canelado' })" />
        </div>
        `
})
export class Footer {

    constructor(public ref: DynamicDialogRef,
      public conf: DynamicDialogConfig) {
      }

    closeDialog(data : any) {
        this.ref.close(null);
    }
    pasarProducto() {
      this.ref.close(this.conf.data.detalle);
    }

}
