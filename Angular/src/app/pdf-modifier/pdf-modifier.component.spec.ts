import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PdfModifierComponent } from './pdf-modifier.component';

describe('PdfModifierComponent', () => {
  let component: PdfModifierComponent;
  let fixture: ComponentFixture<PdfModifierComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PdfModifierComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PdfModifierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
