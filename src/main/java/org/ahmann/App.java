package org.ahmann;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;


@AllArgsConstructor
@Data
class Fsb {
  private int fsbNum;
  private int location;
  private int size;

  public static Comparator<Fsb> sortBylocation = Comparator.comparingInt(obj -> obj.location);
}

public class App extends IoClass {
  private ArrayList<Fsb> fsbArrayList = new ArrayList<>();
  private int rovingPointer;

  public App() {
    // Initialize FSB
    fsbArrayList.add(new Fsb(1, 0, 350));

    // Initialize roving pointer to FSB# 1 location
    rovingPointer = fsbArrayList.get(0).getLocation();
  }

  public void showStatus() {
    output.println();

    output.printf("%4s  %12s %6s", "FSB#", "Location", "Size");
    output.println();
    for (Fsb fsb: fsbArrayList) {
      output.printf("%4d  %12d %6d", fsb.getFsbNum(), fsb.getLocation(), fsb.getSize());
      output.println();
    }
    output.println("Rover is at location " + rovingPointer);
    output.println("------------------------------------------------------------");
  }

  public void getMemory(int allocationSize) {
    output.println("GET_MEMORY IS RUNNING.........");
    output.println("Allocation request for " + allocationSize + " words");
    output.println();

    // minimum allocation size is 2
    if (allocationSize >= 2) {
      Fsb fsb;

      // Get the FSB index that the pointer is located on or default to 0
      int fsbIndex = 0;
      Optional<Fsb> optionalFsb = fsbArrayList.stream().filter(fsb1 -> fsb1.getLocation() == rovingPointer).findFirst();
      if (optionalFsb.isPresent()) {
        fsbIndex = fsbArrayList.indexOf(optionalFsb.get());
      }

      // Loop through FSBs, starting at location of roving pointer
      for (int i = 0; i < fsbArrayList.size(); i++) {

        // Get the fsb
        fsb = fsbArrayList.get(fsbIndex);

        // Check if we need to allocate the whole FSB
        if (fsb.getSize() == allocationSize && fsb.getLocation() == rovingPointer) {


          if (fsbIndex + 1 >= fsbArrayList.size()) {

            fsb = fsbArrayList.get(0);
          }
          else {
            fsb = fsbArrayList.get(fsbIndex + 1);
          }

          output.println("Allocation was successful in location " + fsbArrayList.get(fsbIndex).getLocation());

          rovingPointer = fsb.getLocation();
          fsbArrayList.remove(fsbIndex);

          // reset FSB#'s
          for (int j = 0; j < fsbArrayList.size(); j++) {
            fsbArrayList.get(j).setFsbNum(j + 1);
          }


          break;

        }
        else if (fsb.getSize() >= allocationSize && fsb.getLocation() >= rovingPointer) {
          output.println("Allocation was successful in location " + fsb.getLocation());
          fsb.setLocation(fsb.getLocation() + allocationSize);
          fsb.setSize(fsb.getSize() - allocationSize);

          // Move the roving pointer
          // If the last FSB was allocated, move the pointer to the beginning
          if (fsbIndex + 1 >= fsbArrayList.size()) {
            rovingPointer = fsbArrayList.get(0).getLocation();
          }
          else {
            rovingPointer = fsbArrayList.get(fsbIndex + 1).getLocation();
          }

          break;
        } else {
          output.println("Allocation was NOT successful in location " + fsb.getLocation());
        }

        // Increment the loop
        fsbIndex++;

        // Restart at the beginning of array if the index is bigger than the size of array
        if (fsbIndex >= fsbArrayList.size()) {
          fsbIndex = 0;
        }

      }
      showStatus();
    }
  }

  public void freeMemory(int deAllocationSize, int location) {
    output.println("FREE_MEMORY IS RUNNING.........");
    output.println("De-allocation request for " + deAllocationSize + " words at location " + location);
    // minimum de-allocation size is 2
    if (deAllocationSize >= 2) {
      for (int i = 0; i < fsbArrayList.size(); i++) {
        Fsb fsb = fsbArrayList.get(i);
        // If we want to de-allocate inside a current fsb
        if (fsb.getLocation() > location) {
          fsbArrayList.add(new Fsb(1,location, deAllocationSize));
          rovingPointer = location;
          break;
        }
      }
      // Sort the array on location parameter
      Collections.sort(fsbArrayList, Fsb.sortBylocation);
      // reset FSB#'s
      for (int j = 0; j < fsbArrayList.size(); j++) {
        fsbArrayList.get(j).setFsbNum(j + 1);
      }
    }
      showStatus();
    }
}
