import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Tiendacomponent } from './tiendacomponent';

describe('Tiendacomponent', () => {
  let component: Tiendacomponent;
  let fixture: ComponentFixture<Tiendacomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Tiendacomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Tiendacomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
