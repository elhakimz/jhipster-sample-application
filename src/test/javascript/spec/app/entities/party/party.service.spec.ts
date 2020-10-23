import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PartyService } from 'app/entities/party/party.service';
import { IParty, Party } from 'app/shared/model/party.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { PartyType } from 'app/shared/model/enumerations/party-type.model';

describe('Service Tests', () => {
  describe('Party Service', () => {
    let injector: TestBed;
    let service: PartyService;
    let httpMock: HttpTestingController;
    let elemDefault: IParty;
    let expectedResult: IParty | IParty[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PartyService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Party(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', Gender.MALE, PartyType.PERSON);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            birthDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Party', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Party()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Party', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            birthPlace: 'BBBBBB',
            gender: 'BBBBBB',
            partyType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Party', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            birthPlace: 'BBBBBB',
            gender: 'BBBBBB',
            partyType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Party', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
