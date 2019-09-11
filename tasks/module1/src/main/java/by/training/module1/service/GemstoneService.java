package by.training.module1.service;

import by.training.module1.entity.Gemstone;

import java.util.List;

public interface GemstoneService {
    List<Gemstone> sortValue(List<Gemstone> gemstones);
    List<Gemstone> sortWeight(List<Gemstone> gemstones);
    double sumValue(List<Gemstone> gemstones);
    List<Gemstone> findGemstonesByTransparency(List<Gemstone> gemstones, int transparency);
}
