import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Mensaje } from './mensaje.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MensajeService {

    private resourceUrl = SERVER_API_URL + 'api/mensajes';

    constructor(private http: Http) { }

    create(mensaje: Mensaje): Observable<Mensaje> {
        const copy = this.convert(mensaje);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(mensaje: Mensaje): Observable<Mensaje> {
        const copy = this.convert(mensaje);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Mensaje> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Mensaje.
     */
    private convertItemFromServer(json: any): Mensaje {
        const entity: Mensaje = Object.assign(new Mensaje(), json);
        return entity;
    }

    /**
     * Convert a Mensaje to a JSON which can be sent to the server.
     */
    private convert(mensaje: Mensaje): Mensaje {
        const copy: Mensaje = Object.assign({}, mensaje);
        return copy;
    }
}
