import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mensaje } from './mensaje.model';
import { MensajePopupService } from './mensaje-popup.service';
import { MensajeService } from './mensaje.service';

@Component({
    selector: 'jhi-mensaje-delete-dialog',
    templateUrl: './mensaje-delete-dialog.component.html'
})
export class MensajeDeleteDialogComponent {

    mensaje: Mensaje;

    constructor(
        private mensajeService: MensajeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mensajeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mensajeListModification',
                content: 'Deleted an mensaje'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mensaje-delete-popup',
    template: ''
})
export class MensajeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mensajePopupService: MensajePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mensajePopupService
                .open(MensajeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
