function [ equal ] = comparePixel( pixel1, pixel2, JND )
%COMPAREPIXEL Summary of this function goes here
%   Detailed explanation goes here
    
%pixel 1 = background
%pixel 2 = foreground
    E = sqrt( (pixel1(1,1,1)-pixel2(1,1,1))^2 + ...
                (pixel1(1,1,2)-pixel2(1,1,2))^2 + ...
                (pixel1(1,1,3)-pixel2(1,1,3))^2);
    if(E > JND)
        equal = 0;
    else
        equal = 1;
    end
          

end

