function [ out ] = main( )
%MAIN Summary of this function goes here
%   Detailed explanation goes here
    img = imread('/Users/bradmiller/Documents/MATLAB/534_final/Fire-Hydrant-Installation-Schererville-IN-300x200.jpg');
    %img = imread('tiger.jpg');
    [m,n,k] = size(img);
    backgroundBox = zeros(m,n);
    pixel1 = {50,10};
    pixel2 = {200,100};
    pixel3 = {50,190};
    pixel4 = {200,190};
    
    for i=pixel1{1}:pixel2{1}
       for j=pixel1{2}:pixel3{2}
           backgroundBox(j,i,:) = 1;
       end
    end
    
    JND = 3.6;
    
    out = GraphCut2(img, backgroundBox, JND);

end

