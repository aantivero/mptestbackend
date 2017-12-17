import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DebinappCuentaModule } from './cuenta/cuenta.module';
import { DebinappMensajeModule } from './mensaje/mensaje.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        DebinappCuentaModule,
        DebinappMensajeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DebinappEntityModule {}
