import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContactMechPposType, ContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';
import { ContactMechPposTypeService } from './contact-mech-ppos-type.service';

@Component({
  selector: 'jhi-contact-mech-ppos-type-update',
  templateUrl: './contact-mech-ppos-type-update.component.html',
})
export class ContactMechPposTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: [],
  });

  constructor(
    protected contactMechPposTypeService: ContactMechPposTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMechPposType }) => {
      this.updateForm(contactMechPposType);
    });
  }

  updateForm(contactMechPposType: IContactMechPposType): void {
    this.editForm.patchValue({
      id: contactMechPposType.id,
      code: contactMechPposType.code,
      description: contactMechPposType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactMechPposType = this.createFromForm();
    if (contactMechPposType.id !== undefined) {
      this.subscribeToSaveResponse(this.contactMechPposTypeService.update(contactMechPposType));
    } else {
      this.subscribeToSaveResponse(this.contactMechPposTypeService.create(contactMechPposType));
    }
  }

  private createFromForm(): IContactMechPposType {
    return {
      ...new ContactMechPposType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactMechPposType>>): void {
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
}
