import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MensajeComponent } from './mensaje.component';
import { MensajeDetailComponent } from './mensaje-detail.component';
import { MensajePopupComponent } from './mensaje-dialog.component';
import { MensajeDeletePopupComponent } from './mensaje-delete-dialog.component';

export const mensajeRoute: Routes = [
    {
        path: 'mensaje',
        component: MensajeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.mensaje.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mensaje/:id',
        component: MensajeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.mensaje.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mensajePopupRoute: Routes = [
    {
        path: 'mensaje-new',
        component: MensajePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.mensaje.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mensaje/:id/edit',
        component: MensajePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.mensaje.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mensaje/:id/delete',
        component: MensajeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.mensaje.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
