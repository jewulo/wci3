package wci.backend.interpreter;

public interface ActivationRecord {
    int getNestingLevel();

    ActivationRecord makeLinkTo(ActivationRecord prevAr);

    ActivationRecord linkedTo();
}
