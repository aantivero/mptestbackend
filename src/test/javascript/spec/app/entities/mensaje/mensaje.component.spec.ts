/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { DebinappTestModule } from '../../../test.module';
import { MensajeComponent } from '../../../../../../main/webapp/app/entities/mensaje/mensaje.component';
import { MensajeService } from '../../../../../../main/webapp/app/entities/mensaje/mensaje.service';
import { Mensaje } from '../../../../../../main/webapp/app/entities/mensaje/mensaje.model';

describe('Component Tests', () => {

    describe('Mensaje Management Component', () => {
        let comp: MensajeComponent;
        let fixture: ComponentFixture<MensajeComponent>;
        let service: MensajeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DebinappTestModule],
                declarations: [MensajeComponent],
                providers: [
                    MensajeService
                ]
            })
            .overrideTemplate(MensajeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MensajeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MensajeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Mensaje(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mensajes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
