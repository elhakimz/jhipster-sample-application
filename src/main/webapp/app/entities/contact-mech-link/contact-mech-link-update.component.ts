import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContactMechLink, ContactMechLink } from 'app/shared/model/contact-mech-link.model';
import { ContactMechLinkService } from './contact-mech-link.service';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';

@Component({
  selector: 'jhi-contact-mech-link-update',
  templateUrl: './contact-mech-link-update.component.html',
})
export class ContactMechLinkUpdateComponent implements OnInit {
  isSaving = false;
  contactmeches: IContactMech[] = [];

  editForm = this.fb.group({
    id: [],
    toContactMechanism: [],
    fromContactMechanism: [],
  });

  constructor(
    protected contactMechLinkService: ContactMechLinkService,
    protected contactMechService: ContactMechService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMechLink }) => {
      this.updateForm(contactMechLink);

      this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactmeches = res.body || []));
    });
  }

  updateForm(contactMechLink: IContactMechLink): void {
    this.editForm.patchValue({
      id: contactMechLink.id,
      toContactMechanism: contactMechLink.toContactMechanism,
      fromContactMechanism: contactMechLink.fromContactMechanism,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactMechLink = this.createFromForm();
    if (contactMechLink.id !== undefined) {
      this.subscribeToSaveResponse(this.contactMechLinkService.update(contactMechLink));
    } else {
      this.subscribeToSaveResponse(this.contactMechLinkService.create(contactMechLink));
    }
  }

  private createFromForm(): IContactMechLink {
    return {
      ...new ContactMechLink(),
      id: this.editForm.get(['id'])!.value,
      toContactMechanism: this.editForm.get(['toContactMechanism'])!.value,
      fromContactMechanism: this.editForm.get(['fromContactMechanism'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactMechLink>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IContactMech): any {
    return item.id;
  }
}
