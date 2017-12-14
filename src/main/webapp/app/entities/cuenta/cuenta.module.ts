import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DebinappSharedModule } from '../../shared';
import { DebinappAdminModule } from '../../admin/admin.module';
import {
    CuentaService,
    CuentaPopupService,
    CuentaComponent,
    CuentaDetailComponent,
    CuentaDialogComponent,
    CuentaPopupComponent,
    CuentaDeletePopupComponent,
    CuentaDeleteDialogComponent,
    cuentaRoute,
    cuentaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cuentaRoute,
    ...cuentaPopupRoute,
];

@NgModule({
    imports: [
        DebinappSharedModule,
        DebinappAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CuentaComponent,
        CuentaDetailComponent,
        CuentaDialogComponent,
        CuentaDeleteDialogComponent,
        CuentaPopupComponent,
        CuentaDeletePopupComponent,
    ],
    entryComponents: [
        CuentaComponent,
        CuentaDialogComponent,
        CuentaPopupComponent,
        CuentaDeleteDialogComponent,
        CuentaDeletePopupComponent,
    ],
    providers: [
        CuentaService,
        CuentaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DebinappCuentaModule {}
