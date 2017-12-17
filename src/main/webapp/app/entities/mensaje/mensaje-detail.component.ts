import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Mensaje } from './mensaje.model';
import { MensajeService } from './mensaje.service';

@Component({
    selector: 'jhi-mensaje-detail',
    templateUrl: './mensaje-detail.component.html'
})
export class MensajeDetailComponent implements OnInit, OnDestroy {

    mensaje: Mensaje;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mensajeService: MensajeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMensajes();
    }

    load(id) {
        this.mensajeService.find(id).subscribe((mensaje) => {
            this.mensaje = mensaje;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMensajes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mensajeListModification',
            (response) => this.load(this.mensaje.id)
        );
    }
}
