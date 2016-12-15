%This poisson blender was written with inspiration from:
%http://eric-yuan.me/poisson-blending/
%http://cs.brown.edu/courses/cs129/asgn/proj2/
%https://github.com/asteroidhouse/gradient-blend

function result = Blender(source, target, mask)
%These calculations are going to use index form.
%If the mask touches the edge, we're gonna have a bad time.

[r, c, ~] = size(target);
%These will be bigger, but we want them to behave as having one column.
rows = zeros(r*c, 1);
cols = zeros(r*c, 1);
vals = zeros(r*c, 1);

mapping = 1;

for i = 1:r*c
    if (mask(i))
        %Map the pixel and its four neighbors.
        rows(mapping) = i;
        cols(mapping) = i;
        vals(mapping) = 4;
        mapping = mapping +1;
        
        rows(mapping) = i;
        cols(mapping) = i + 1;
        vals(mapping) = -1;
        mapping = mapping +1;
        
        rows(mapping) = i;
        cols(mapping) = i - 1;
        vals(mapping) = -1;
        mapping = mapping +1;
        
        %Since we're in index form we need to move index by r.
        rows(mapping) = i;
        cols(mapping) = i + r;
        vals(mapping) = -1;
        mapping = mapping +1;
        
        rows(mapping) = i;
        cols(mapping) = i - r;
        vals(mapping) = -1;
        mapping = mapping +1;
    else
        %In the dark part of the mask, we get the same as background
        rows(mapping) = i;
        cols(mapping) = i;
        vals(mapping) = 1;
        mapping = mapping +1;
    end
end

A = sparse(rows, cols, vals, r*c, r*c);

red = Blend1Channel(source(:,:,1), target(:,:,1), mask, A);
green = Blend1Channel(source(:,:,2), target(:,:,2), mask, A);
blue = Blend1Channel(source(:,:,3), target(:,:,3), mask, A);

result = zeros(size(target));
result(:,:,1) = reshape(red, size(target(:, :, 1)));
result(:,:,2) = reshape(green, size(target(:, :, 2)));
result(:,:,3) = reshape(blue, size(target(:, :, 3)));